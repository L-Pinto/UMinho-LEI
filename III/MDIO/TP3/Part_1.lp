// custo associado à redução das durações das actividades
min: 200 r0_1 + 100 r0_2 + 200 r3_1 + 100 r3_2 + 800 r4_1 + 400 r4_2 + 1600 r5_1 + 800 r5_2 +
     180 r6_1 + 90 r6_2 + c7 + 200 r8_1 + 100 r8_2 + c9 + 1000 r10_1 + 500 r10_2 +
     600 r11_1 + 300 r11_2;

// tempo máximo para concluir o projecto
tf <= 23;

/* relações de precedência na restrição tj >= ti - ri + di.
A função ti - ri + di designa o tempo de conclusão da atividade i
após a redução da duração, de di para -ri + di */
arco_i0: t0 >= ti + 0 ;
arco_03: t3 >= t0 - r0 + 4 ;
arco_04: t4 >= t0 - r0 + 4 ;
arco_43: t3 >= t4 - r4 + 9 ;
arco_53: t3 >= t5 - r5 + 4 ;
arco_3f: tf >= t3 - r3 + 2 ;
arco_45: t5 >= t4 - r4 + 9 ;
arco_5f: tf >= t5 - r5 + 4 ;
arco_i6: t6 >= ti + 0 ;
arco_74: t4 >= t7 + d7;
arco_85: t5 >= t8 - r8 + 4 ;
arco_9f: tf >= t9 + d9;
arco_67: t7 >= t6 - r6 + 5 ;
arco_78: t8 >= t7 + d7;
arco_89: t9 >= t8 - r8 + 4 ;
arco_610: t10 >= t6 - r6 + 5 ;
arco_108: t8 >= t10 - r10 + 8 ;
arco_119: t9 >= t11 - r11 + 7 ;
arco_1011: t11 >= t10 - r10+ 8;

// Redução Total para as atividades 0,3,4,5,6,8,10,11
r0 = r0_1 + r0_2;
r3 = r3_1 + r3_2;
r4 = r4_1 + r4_2;
r5 = r5_1 + r5_2;
r6 = r6_1 + r6_2;
r8 = r8_1 + r8_2;
r10 = r10_1 + r10_2;
r11 = r11_1 + r11_2;

/* REDUÇOES */

// reduções máximas permitidas para c1
r0_1 <= 0.5;
r0_1 >= 0.5 y0;

r3_1 <= 0.5;
r3_1 >= 0.5 y3;

r4_1 <= 2;
r4_1 >= 2 y4;

r5_1 <= 0.5;
r5_1 >= 0.5 y5;

r6_1 <= 1;
r6_1 >= 1 y6;

r8_1 <= 0.5;
r8_1 >= 0.5 y8;

r10_1 <= 0.5;
r10_1 >= 0.5 y10;

r11_1 <= 1;
r11_1 >= 1 y11;

// reduções máximas permitidas para c2
r0_2 <= 0.5 y0;
r3_2 <= 0.5 y3;
r4_2 <= 1 y4;
r5_2 <= 0.5 y5;
r6_2 <= 1 y6;
r8_2 <= 0.5 y8;
r10_2 <= 0.5 y10;
r11_2 <= 1 y11;

/* OPÇÕES PARA AS ATIVIDADES 7 E 9 */

// custo de cada opção para 7 e 9
c7 = 300op1_7 + 1100op2_7 + 0op3_7;
c9 = 200op1_9 + 400op2_9 + 0op3_9;

// duração de cada opção para 7 e 9
d7 = 5op1_7 + 4op2_7 + 6op3_7;
d9 = 1op1_9 + 0op2_9 + 2op3_9;

// escolha da opção para 7 e 9
op1_7 + op2_7 + op3_7 = 1;
op1_9 + op2_9 + op3_9 = 1;

bin
op1_9, op2_9, op3_9,
op1_7, op2_7, op3_7,
y0, y3, y4, y5, y6,
y8, y10, y11;