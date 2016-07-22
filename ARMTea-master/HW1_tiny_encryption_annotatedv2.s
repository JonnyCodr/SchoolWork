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
; * Note: all addition and subtraction is mod 2^32 based unless noted
; ****************************************************************************

	AREA 	homework_1, CODE

SWI_WriteC	EQU	&0     					;output character in r0 
SWI_Exit		EQU	&11    					;finish program
NULL				EQU	0
MAX_LEN 		EQU 32
		
	ENTRY
Main
;================================ Encrypt =====================================

	ldr r1, LZero 							;r1 <- L0
	ldr r2, RZero 							;r2<- R0

	ldr r3, DeltaOne 							 
	ldr r4, DeltaTwo							
	ldr r5, KZero							
	ldr r6, KOne 							
	ldr r7, KTwo 						
	ldr r8, KThree

enFeistelOne
	mov r11, r2, lsl #4 		;(RO << 4)
	add r12, r5, r11  			;(RO << 4) + K0
	add r13, r2, r3 				;R0 + DeltaOne
	mov r11, r0, lsr #5			;(RO >> 5)
	add r9, r6, r11					;(RO >> 5) + K1
	eor r10, r12, r13 			;((RO << 4) + K0) XOR (R0 + DeltaOne)
	eor r10, r10, r9 				;((RO << 4) + K0) XOR (R0 + DeltaOne) XOR ((RO >> 5) + K1)

	add r1, r1, r10 				;r1 := L0 + [((RO << 4) + K0) XOR (R0 + DeltaOne) XOR ((RO >> 5) + K1)]

enFeistelTwo
	mov r11, r1, lsl #4 		;(R1 << 4) [L0 === R1]
	add r12, r7, r11 				;(R1 << 4) + K2
	add r13, r1, r4					;R1 + DeltaTwo
	mov r11, r1, lsr #5 			;(R1 >> 5)
	add r9, r11, r8 				;(R1 >> 5) + K3
	eor r10, r12, r13 			;((R1 << 4) + K2) XOR (R1 + DeltaTwo)
	eor r10, r10, r9 				;((R1 << 4) + K2) XOR (R1 + DeltaTwo) XOR ((R1 >> 5) + K3)

	add r2, r2, r10 				;r2 := L1 + [((R1 << 4) + K2) XOR (R1 + DeltaTwo) XOR ((R1 >> 5) + K3)]
													; [R0 === L1]

movToMemory
	adr r9, LTwo
	adr r10, RTwo
	str r1, [r9]

	str r2, [r10] 
	
	ldr r0, LTwo
	ldr r1, RTwo

;------------------------------ decrypt ---------------------------------------

	ldr r1, LTwo 							;r1 <- L0
	ldr r2, RTwo 							;r2<- R0

	ldr r3, DeltaOne 							 
	ldr r4, DeltaTwo							
	ldr r5, KZero							
	ldr r6, KOne 							
	ldr r7, KTwo 						
	ldr r8, KThree

deFeiselOne
	mov r11, r1, lsr #5
	add r9, r8, r11
	add r13, r1, r4
	mov r11, r1, lsl #4
	add r12, r11, r7
	eor r10, r12, r13
	eor r10, r10, r9

	rsb r2, r10, r2

deFeiselTwo
	mov r11, r2, lsr #5
	add r9, r6, r11
	add r13, r2, r3
	mov r11, r2, lsl #4
	add r12, r11, r5

	eor r10, r12, r13
	eor r10, r10, r9

	rsb r1, r10, r1

	adr r3, LTwo
	adr r4, RTwo
	str r1, [r3]
	str r2, [r4]

	ldr r1, LTwo
	ldr r2, RTwo



	SWI SWI_Exit						;finished
	ALIGN

;==============================================================================

innerFunc


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
	DCD 0xACEFACE1
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