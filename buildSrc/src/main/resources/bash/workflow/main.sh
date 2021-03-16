#!/bin/bash

echo "main start..."

export WORKFLOW=$RESOURCES_PATH/bash/workflow

. $WORKFLOW/vcs.sh || exit 1
exit 1 # todo

echo "main success"

exit 0
