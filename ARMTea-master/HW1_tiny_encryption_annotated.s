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
; * Note: all addition and subtraction is mod 2^n based unless noted
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

	ldr r3, KZero 							;r3 <- K0 
	ldr r4, DeltaOne 						;r4 <- Delta1
	ldr r5, KOne 								;r5 <- K1

	mov r10, r2, lsl #4					;r10 <- the contents of r2 shifted 4 bits
	add r3, r3, r10 						;r3 <- KZero + (RZero(r10) << 4)
	add r4, r2, r4 							;r4 <- RZero + DeltaOne
	mov r10, r2, lsr #5 				;r10 <- RZero shifted right 5 bits
	add r5, r5, r10 						;r5 <- KOne + (RZero(r0) >> 5)
	eor r3, r3, r4 							;r3 <- KOne + (RZero(r0) >> 5) 
															; 							XOR (RZero + Delta1)
	eor r3, r3, r5 							;r3 <- KOne + (RZero(r0) >> 5) 
															; 					XOR (RZero + Delta1) 
															; 					XOR KOne + (RZero(r0) >> 5)

	add r2, r1, r3 							;r2 <- L0 + [KOne + (RZero(r0) >> 5) 
															; 						XOR (RZero + Delta1) 
															; 						XOR KOne + (RZero(r0) >> 5)]

	ldr r1, RZero 							;r1 <- RZero
	ldr r3, KTwo 								;r3 <- KTwo
	ldr r4, DeltaTwo 						;r4 <- DeltaTwo
	ldr r5, KThree  						;r5 <- KThree

	mov r10, r2, lsl #4 				;r10 <- [results from L0 encrypted << 4]
	add r3, r3, r10 						;r3 <- KTwo + (L0 << 4)
	add r4, r2, r4 							;r4 <- (Delta2 + [results from L0 encrypted])
	mov r10, r2, lsr #5 				;r10 <- ([results from L0 encrypted] >> 5)
	add r5, r5, r10 						;r5 <- K3 + ([results from L0 encrypted] >> 5)
	eor r3, r3, r4 							;r3 <- (KTwo + (L1 << 4)) 
															; 				XOR (Delta2 + L1) XOR (K3 + L1)
	eor r3, r3, r5 							;r3 <- (KTwo + (L0 << 4)) 
															; 	XOR (Delta2 + L1)
															; 	XOR (K3 + (L1 >> 5))

	add r1, r1, r3 							;r1 <- RZero + [(KTwo + (L0 << 4)) 
															; 	XOR (Delta2 + L1)
															; 	XOR (K3 + (L1 >> 5))]

;check point: r2 has L2(cypherText of L0) && r1 has R2(cypherText of R0)

saveCipherText
	adr r0, LTwo
	str r2, [r0]
	adr r0, RTwo
	str r1, [r0]

;================================ Decrypt =====================================
	ldr r1, LTwo 					;r1 <- LTwo
	ldr r2, RTwo 					;r2 <- RTwo

	ldr r3, KTwo 					;r3 <- KTwo
	ldr r4, DeltaTwo 			;r4 <- DeltaTwo
	ldr r5, KThree 				;r5 <- KThree

	mov r10, r2, lsl #4 	;r10 <- (LTwo << 4)
	add r3, r3, r10 			;r3 <- KTwo + (LTwo << 4)
	add r4, r2, r4 				;r4 <- Delta2 + RTwo
	mov r10, r2, lsr #5 	;r10 <- (RTwo >> 5)
	add r5, r5, r10 			;r5 <- KThree + (LTwo >> 5)
	eor r3, r3, r4 				;r3 <- (KTwo + (LTwo << 4)) XOR (Delta2 + LTwo)
	eor r3, r3, r5  			;r3 <- (KTwo + (LTwo << 4)) XOR (Delta2 + LTwo)
	 											; 					XOR (KThree + (RTwo >> 5))
	rsb r2, r1, r3 				;r2 <- LTwo - [(KTwo + (RTwo << 4)) XOR (Delta2 + LTwo)
	 											; 					XOR (KThree + (LTwo >> 5))]

	ldr r1, RTwo 					;r1 <- RTwo
	ldr r3, KZero 				;r3 <- KZero
	ldr r4, DeltaOne 			;r4 <- Delta1
	ldr r5, KOne 					;r5 <- KOne

	mov r10, r2, lsl #4 	;r10 <- (R1 << #4)
	add r3, r3, r10 			;r3 <- KZero + (R1 << #4)
	add r4, r2, r4 				;r4 <- R1 + DeltaOne
	mov r10, r2, lsr #5 	;r10 <- (R1 >> #5)
	add r5, r5, r10 			;r5 <- KOne + (R1 >> #5)
	eor r3, r3, r4 				;r3 <- (KZero + (R1 << #4)) XOR (R1 + DeltaOne)
	eor r3, r3, r5 				;r3 <- (KZero + (R1 << #4)) 
	 											; 				XOR (R1 + DeltaOne)
	 											; 				XOR (KOne + (R1 >> #5))

	rsb r1, r1, r3 				;r1 <- RTwo - [(KZero + (R1 << #4)) 
	 											; 				XOR (R1 + DeltaOne)
	 											; 				XOR (KOne + (R1 >> #5))]

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