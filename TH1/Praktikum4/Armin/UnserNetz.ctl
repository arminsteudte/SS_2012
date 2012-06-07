A((Bereit1==1)  U (Bereit0==1 & Bereit1==0)) -> AX(Bereit0==1);

(Bereit0==1) -> A((Bereit0==1) U (Bereit1==1));

(Bereit1==0 & AufLeitung1==1 & Bereit0==1) -> !EX(Bereit1==1 & BereitAck1==1 & AufLeitung1==0);

((AufLeitung1==1) -> AX((BereitAck1==0) | (BereitAck1==1))) |
((AufLeitung0==1) -> AX((BereitAck0==0) | (BereitAck0==1)));