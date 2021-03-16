echo "build start..."

docker image prune -f
CODE=0
export DOCKER_IMAGE_NAME=docker_${GITHUB_RUN_NUMBER}_${GITHUB_RUN_ID}_image
docker build --no-cache \
 -t $DOCKER_IMAGE_NAME \
 -f $RESOURCES_PATH/docker/Dockerfile . || CODE=$?
if test $CODE -ne 0; then
 echo "Build error $CODE!"; return 1
fi

export DOCKER_CONTAINER_NAME=docker_${GITHUB_RUN_NUMBER}_${GITHUB_RUN_ID}_container
export ASSEMBLY_PATH=assembly_${GITHUB_RUN_NUMBER}_${GITHUB_RUN_ID}
rm -rf $ASSEMBLY_PATH
mkdir -p $ASSEMBLY_PATH || return 2

. $WORKFLOW/build/assembly.sh || CODE=$?
if test $CODE -ne 0; then
 echo "Assembly error $CODE!"; return 3
fi

export VERSION_NAME=""
export VERSION_CODE=""
export APPLICATION_ID=""
while IFS='=' read -r k v; do
  case "$k" in
   versionName) VERSION_NAME=$v;;
   versionCode) VERSION_CODE=$v;;
   applicationId) APPLICATION_ID=$v;;
  esac
done < $ASSEMBLY_PATH/assembly/summary

if test -z "$VERSION_NAME"; then
 echo "Assembly summary file must contains VERSION_NAME!"; return 41
fi
if test -z "$VERSION_CODE"; then
 echo "Assembly summary file must contains VERSION_CODE!"; return 42
fi
if test -z "$APPLICATION_ID"; then
 echo "Assembly summary file must contains APPLICATION_ID!"; return 43
fi

echo "build success"

return 0
