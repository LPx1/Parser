grammar FeatherweightJavaScript;


@header { package edu.sjsu.fwjs.parser; }

// Reserved words
IF        : 'if' ;
ELSE      : 'else' ;
WHILE     : 'while' ;
FUNCTION  : 'function' ;
VAR       : 'var' ;
PRINT     : 'print' ;

// Literals
INT       : [1-9][0-9]* | '0' ;
BOOL      : 'true' | 'false' ;
NULL      : 'null' ;

// Symbols
MUL       : '*' ;
DIV       : '/' ;
ADD       : '+' ;
SUB       : '-' ;
MOD       : '%' ;
GT        : '>' ;
LT        : '<' ;
GE        : '>=' ;
LE        : '<=' ;
EQ        : '==' ;
SEPARATOR : ';' ;

// Identifier
IDENTIFIER : [a-zA-Z_][a-zA-Z_0-9]*;


// Whitespace and comments
NEWLINE   : '\r'? '\n' -> skip ;
BLOCK_COMMENT : '/*' .*? '*/'  -> skip ;
LINE_COMMENT  : '//' ~[\n\r]* -> skip ;
WS            : [ \t]+ -> skip ; // ignore whitespace


// ***Paring rules ***

/** The start rule */
prog: stat+ ;

stat: expr SEPARATOR                                    # bareExpr
    | IF '(' expr ')' block ELSE block                  # ifThenElse
    | IF '(' expr ')' block                             # ifThen
    | WHILE '(' expr ')' block                          # while
    | PRINT '(' expr ')' SEPARATOR                      # print
    ;

expr: expr op=( '*' | '/' | '%' ) expr                  # MulDivMod
    | INT                                               # int
    | '(' expr ')'                                      # parens
    | expr op=( '+' | '-' ) expr                        # addsub
    | expr op=( '>' | '<' | '<=' | '>=' | '==') expr    # compare
    | FUNCTION '(' (expr)? (',' expr)* ')' block        # funcDec
    | IDENTIFIER '(' (expr)? (',' expr)* ')'            # funcApp
    | VAR IDENTIFIER ASGN expr                          # varDec
    | IDENTIFIER                                        # varApp
    | IDENTIFIER ASGN expr                              # varAsgn
    | BOOL                                              # bool
    | NULL                                              # null
    ;

block: '{' stat* '}'                                    # fullBlock
     | stat                                             # simpBlock
     ;

