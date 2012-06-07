A((Bit1==1)  U (Bit0==1 & Bit1==0)) -> AX(Bit0==1);

(Bit0==1) -> A((Bit0==1) U (Bit1==1));

(Bit1==0 & h1==1 & Bit0==1) -> !EX(Bit1==1 & Bit1==1 & h1==0);

((h1==1) -> AX((SendeAck1==0) | (SendeAck1==1))) | 
((h2==1) -> AX((SendAck0==0) | (SendAck0==1)));
	
