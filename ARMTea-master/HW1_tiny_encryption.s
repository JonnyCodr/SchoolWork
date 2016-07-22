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
; ****************************************************************************

	AREA 	homework_1, CODE

SWI_WriteC	EQU	&0     					;output character in r0 
SWI_Exit		EQU	&11    					;finish program
NULL				EQU	0
MAX_LEN 		EQU 32
		
	ENTRY
Main
;================================ Encrypt =====================================

	ldr r1, =LZero
	ldr r2, =RZero

	ldr r3, KZero
	ldr r4, DeltaOne
	ldr r5, KOne

	bl innerFunc
	add r2, r1, r3

	ldr r1, =RZero

	ldr r3, KTwo
	ldr r4, =DeltaTwo
	ldr r5, =KThree

	bl innerFunc
	add r1, r1, r3
;at this point r2 has L2 && r1 has R2

saveCipherText
	adr r0, =LTwo
	str r2, [r0]
	adr r0, =RTwo
	str r1, [r0]

;================================ Decrypt =====================================
	ldr r1, =LTwo
	ldr r2, =RTwo

	ldr r3, =KTwo
	ldr r4, =DeltaTwo
	ldr r5, =KThree

	bl innerFunc
	sub r2, r1, r3

	ldr r1, =RTwo

	bl innerFunc
	sub r1, r1, r3
	;check point: the plaintext should be what we started with

savePlainText
	

	SWI SWI_Exit						;finished
	ALIGN
;==============================================================================

innerFunc
	mov r10, r2, lsl #4
	add r3, r3, r10
	add r4, r2, r4
	mov r10, r2, lsr #5
	add r5, r5, r10
	eor r3, r3, r4
	eor r3, r3, r5
	mov pc, lr	

;*****************************************************************************
	AREA Data1, DATA

DeltaOne    
	DCD 0x11111111

DeltaTwo 
	DCD 0x22222222

KZero
	DCD 0xACE01942
KOne
	DCD 0xABADFACE
KTwo
	DCD 0xFEEDBACD
KThree
 	DCD 0xFABBAB09

LZero	
	DCD 0xACEFACE1
LZeroEnd DCB	NULL
	ALIGN

RZero	
	DCD &BADACE01
RZeroEnd DCB	NULL
	ALIGN

LTwo
		% MAX_LEN
LTwoEnd 	DCB NULL
	ALIGN

RTwo
		% MAX_LEN
RTwoEnd 	DCB NULL
	ALIGN
;*****************************************************************************
	END