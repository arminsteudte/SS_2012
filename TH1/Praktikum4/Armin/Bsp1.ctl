A((Sender_1==1)  U (Sender_0==1 & Sender_1==0)) -> AX(Sender_0==1);

(Sender_0==1) -> A((Sender_0==1) U (Sender_1==1));

(Sender_1==0 & Message_1==1 & Sender_0==1) -> 
!EX(Sender_1==1 & Rec_1==1 & Message_1==0);

((Message_1==1) -> AX((Rec_1==0) | (Rec_1==1))) |
((Message_0==1) -> AX((Rec_0==0) | (Rec_0==1)));
