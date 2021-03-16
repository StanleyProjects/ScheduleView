#!/bin/bash

echo "main start..."

ERROR_CODE_COMMON_FILE=11
ERROR_CODE_VCS=21

export WORKFLOW=$RESOURCES_PATH/bash/workflow

FILE="$WORKFLOW/vcs.sh"
if [ ! -f "$FILE" ]; then
 echo "Version control system file \"$FILE\" error $CODE!"
 exit $ERROR_CODE_COMMON_FILE
fi

. $FILE
CODE=$?
if test $CODE -ne 0; then
 echo "Version control system error $CODE!"
 exit $ERROR_CODE_VCS
fi

exit 1 # todo

echo "main success"

exit 0
