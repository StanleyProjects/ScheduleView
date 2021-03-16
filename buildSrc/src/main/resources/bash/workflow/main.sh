#!/bin/bash

echo "main start..."

ERROR_CODE_VCS=21
ERROR_CODE_SETUP=22
CODE=0

export WORKFLOW=$RESOURCES_PATH/bash/workflow

. $WORKFLOW/vcs.sh
CODE=$?
if test $CODE -ne 0; then
 echo "Version control system error $CODE!"
 exit $ERROR_CODE_VCS
fi

. $WORKFLOW/setup.sh
CODE=$?
if test $CODE -ne 0; then
 echo "Setup error $CODE!"
 exit $ERROR_CODE_SETUP
fi

exit 1 # todo

echo "main success"

exit 0
