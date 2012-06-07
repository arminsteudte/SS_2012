A(Sender_1 == 1 U  AF Ack_1==1) -> AF Sender_0==1;

Sender_0 == 1 -> A(Message_0==1 U Sender_1==1);

AG(Sender_0==1 & Message_1==1) -> AG Ack_1==0;

AF(Sender_0==1 -> A(Ack_0==1 U Sender_1==1));
