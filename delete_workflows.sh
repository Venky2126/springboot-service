#!/bin/bash

OWNER="Venky2126"
REPO="springboot-service"

# Fetch all workflow run IDs
workflow_runs=$(gh api repos/$OWNER/$REPO/actions/runs --paginate --jq '.workflow_runs[].id')

# Delete each workflow run
for run_id in $workflow_runs; do
  echo "Deleting workflow run ID: $run_id"
  gh api repos/$OWNER/$REPO/actions/runs/$run_id -X DELETE
done

echo "All workflow runs have been deleted."
