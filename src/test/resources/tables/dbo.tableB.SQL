create table dbo.tableB (
  f1 int,
  f2 varchar(256),
  constraint tblb_fk foregn key f1 references tablea(f1)
);

