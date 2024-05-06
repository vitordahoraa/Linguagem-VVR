package Gals.SemanticUtils;

public class SemanticTable {


    // TIPO DE RETORNO DAS EXPRESSOES ENTRE TIPOS
    // 5 x 5 X 5  = TIPO X TIPO X OPER
    static int expTable [][][] =
        {/*       INT       */ /*       FLOAT     */ /*      CHAR       */ /*      STRING     */ /*     BOOL        */
/*   INT*/  {
                {
                    ReferenceValueType.INT.getVarCode(),
                    ReferenceValueType.INT.getVarCode(),
                    ReferenceValueType.INT.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.BOOL.getVarCode()
                },
                {
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.BOOL.getVarCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                }
            },
/* FLOAT*/ {
                {
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.BOOL.getVarCode()
                },
                {
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.DOUBLE.getVarCode(),
                    ReferenceValueType.BOOL.getVarCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                }
            },
/*  CHAR*/ {
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                }
            },
/*STRING*/ {
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReferenceValueType.STRING.getVarCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReferenceValueType.BOOL.getVarCode()
                },
                {
                    ReferenceValueType.STRING.getVarCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReferenceValueType.BOOL.getVarCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                }
            },
/*  BOOL*/ {
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()},
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode()
                },
                {
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReturnType.ERR.getCode(),
                    ReferenceValueType.BOOL.getVarCode()
                }
            }
            };

    // atribuicoes compativeis
    // 5 x 5 = TIPO X TIPO
    static int atribTable [][]={/* INT FLO CHA STR BOO  */
            /*INT*/ {ReturnType.OK_.getCode(),ReturnType.WAR.getCode(),ReturnType.ERR.getCode(),ReturnType.ERR.getCode(),ReturnType.ERR.getCode()},
            /*FLO*/ {ReturnType.OK_.getCode(),ReturnType.OK_.getCode(),ReturnType.ERR.getCode(),ReturnType.ERR.getCode(),ReturnType.ERR.getCode()},
            /*CHA*/ {ReturnType.ERR.getCode(),ReturnType.ERR.getCode(),ReturnType.OK_.getCode(),ReturnType.WAR.getCode(),ReturnType.ERR.getCode()},
            /*STR*/ {ReturnType.ERR.getCode(),ReturnType.ERR.getCode(),ReturnType.OK_.getCode(),ReturnType.OK_.getCode(),ReturnType.ERR.getCode()},
            /*BOO*/ {ReturnType.ERR.getCode(),ReturnType.ERR.getCode(),ReturnType.ERR.getCode(),ReturnType.ERR.getCode(),ReturnType.OK_.getCode()}
};

    static int resultType (int TP1, int TP2, int OP){
        return (expTable[TP1][TP2][OP]);
    }

    static int atribType (int TP1, int TP2){
        return (atribTable[TP1][TP2]);
    }
}
