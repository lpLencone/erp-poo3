psql -U postgres -d erp --set ON_ERROR_STOP=on -f ./sql/schema.sql
psql -U postgres -d erp --set ON_ERROR_STOP=on -f ./sql/data.sql
