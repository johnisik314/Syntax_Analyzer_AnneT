PROGRAM ChangeMaker;
  VAR
  Cost:      INTEGER;
  Remainder: INTEGER;
  Dimes : INTEGER;
  CONST remove from
  Dollar := 100;
  (* Make change  for a dollar *)
  BEGIN
  (* Input the Cost *)
  Write('Enter the  cost in cents: ');
  Read(Cost);
  (* Make the  Change in dimes )
  Remainder := 100 - Cost;
  Dimes :=  Remainder DIV 10;
  Remainder == Remainder % 10;
  Remainder =  Remainder MOD 5;
  IF (Remainder = 0) THEN Write (“No money left”) ELSE Write (Remainder);

  END. (*  ChangeMaker *)