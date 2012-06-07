A(Bereit_1==1 U AF Ack_1_erhalten==1) -> AF Bereit_0==1;

Bereit_0==1 -> A(Nachricht_0==1 U Bereit_1==1);

AG(Bereit_0==1 & Nachricht_1==1) -> AG Ack_1_erhalten==0;

AF(Bereit_0==1 -> A(Ack_0==1 U Bereit_1==1));