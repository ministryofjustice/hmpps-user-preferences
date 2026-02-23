#!/bin/bash

# Note: my container is named `postgres`, you may need to change this to your container name.
docker exec postgres /usr/local/bin/psql -d postgres -c 'CREATE SCHEMA hmppsuserpreferences; CREATE SCHEMA preferences; CREATE SCHEMA preferencestest;'
