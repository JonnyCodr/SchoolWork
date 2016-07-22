; ****************************************************************************
; * security_homework_1.s
; *-------------------------------------------------------------------------
; * author: Michael Menard
; * Network Security CS 3750
; * Fall 2015
; *
; * description: This program uses the tiny encryption algorithm to encrypt a 
; *		Message. 
; *
; *    		  ------------------------------------
; *    		  | DCD	print										;op0 |
; *   	   	| DCD	storRegisters						;op1 |
; *      		| DCD	recoverRegisters				;op2 |
; *      		| DCD	encrypt									;op3 |
; *      		| DCD	decrypt									;op4 |
; *      		| DCD permutate 							;op5 |
; *     		------------------------------------
; *
; ****************************************************************************

	AREA 	homework_1, CODE

SWI_WriteC	EQU	&0     					;output character in r0 
SWI_Exit		EQU	&11    					;finish program
NULL				EQU	0
MAX_LEN 		EQU 32
		
	ENTRY
Main

passOne
	LDR r1, =RZero
	MOV r1, r1, LSL #4
	LDR r2, =KZero
	ADD r1, r1, r2
	LDR r3, =RZero
	LDR r2, =DeltaOne
	ADD r2, r2, r3
	EOR r1, r1, r2
	LDR r3, =RZero
	MOV r3, r3, LSR #5
	LDR r4, =KOne
	ADD r2, r3, r4
	EOR r1, r1, r2
	LDR r2, =LZero
	ADD r1, r1, r2
passTwo
	LDR r2, =RZero
	LDR r3, =KTwo
	MOV r4, r1, LSL #4
	ADD r3, r3, r4
	LDR r4, =DeltaTwo
	ADD r4, r4, r1
	EOR r3, r3, r4
	MOV r5, r1, LSR #5
	ADD r4, r4, r5
	EOR r3, r3, r4
	ADD r2, r2, r3
StoreResults
;	STR r1 -> LTwo
;	STR r2, RTwo

	SWI SWI_Exit									;finished
	ALIGN

;*****************************************************************************
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
		% MAX_LEN 		;reserved zeroed memory for encripted string
LTwoEnd 	DCB NULL
	ALIGN

RTwo
		% MAX_LEN 			;reserved zeroed memory for encripted string
RTwoEnd 	DCB NULL
	ALIGN
;*****************************************************************************
	END