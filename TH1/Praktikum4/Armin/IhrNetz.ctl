A((SchalterA1==1)  U (SchalterA0==1 & SchalterA1==0)) -> AX(SchalterA0==1);

(SchalterA0==1) -> A((SchalterA0==1) U (SchalterA1==1));

(SchalterA1==0 & LeitungVonA1==1 & SchalterA0==1) -> !EX(SchalterA1==1 & SchalterB1==1 & LeitungVonA1==0);

((LeitungVonA1==1) -> AX((SchalterB1==0) | (SchalterB1==1))) |
((LeitungVonA0==1) -> AX((SchalterB0==0) | (SchalterB0==1)));
