	AREA 	Example, CODE ; name this block of code
SWI_WriteC	EQU	&0     					;output character in r0 
SWI_Exit	EQU	&11    					;finish program
NULL		EQU	0
MAX_LEN 	EQU 32

	ENTRY							

main
	LDR r1, =LZero
	MOV r2, r1, LSL #4
	ADR r3, LTwo
	STR r2, [r3]
	LDR r4, =LTwo
	
	
	SWI SWI_Exit									;finished
	ALIGN





		AREA Data1, DATA

; declare a word labeled with DeltaOne, initialize it as 0x11111111
DeltaOne    
	DCD 0x11111111

; declare a word labeled with DeltaTwo, initialize it as 0x22222222
DeltaTwo 
	DCD 0x22222222

; declare four words labeled with KZero, KOne, KTwo, and KThree, 
; 	initialize them using the key of your choice 
KZero
	DCD 0xACE01942
KOne
	DCD 0xABADFACE
KTwo
	DCD 0xFEEDBACD
KThree
 DCD 0xFABBAB09

; declare two 
; 	words labeled with LZero and RZero, initialize them according
; 	to the plaintext of your choice
LZero	
	DCD "Network Security homework_1 ", &0a, &0d, NULL
LZeroEnd DCB	NULL
	ALIGN

RZero	
	DCD "<secret message>", &0a, &0d, NULL
RZeroEnd DCB	NULL
	ALIGN

; declare two word labeled with LTwo and RTwo, initialize them as 0’s

LTwo
	DCD &00000000
LTwoEnd 	DCB NULL
	ALIGN

RTwo
		% MAX_LEN + 1 		;reserved zeroed memory for encripted string
RTwoEnd 	DCB NULL
	ALIGN
;*****************************************************************************
	END