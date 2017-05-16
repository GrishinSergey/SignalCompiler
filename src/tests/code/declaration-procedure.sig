PROCEDURE PROC3(INTEGER);

LABEL 123, 124;
VAR A,B:INTEGER;
    SJDKFL:FLOAT;
DEFFUNC CALCSIN = SIN \ 10, 5;
PROCEDURE PROC1(INTEGER, INTEGER);
PROCEDURE PROC2;

BEGIN
    LOOP
        GOTO 123;
    ENDLOOP;
    ;;;;RETURN;;;
    LINK A, 5678;

    ($ /home/sergey/Java/projects/JavaNative/Compiler/asm-insert.asm $)

    123: IN 1265;
    OUT 1256;

    PROC1(A,B);
    PROC2;

END.