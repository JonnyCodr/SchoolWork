;**************************************************************
; File name: Homework_4
; Name: Michael Menard
; Date: Oct 2, 2014
; CS2400
; project: Homework 4
; description: converts a IEEE single precision floating point 
;		number to a TNS single precision floating
;		point number, and back again. then checks for 
;		equivelance.
;
;		Stored IEEE number for check 	r1
;		Stored TNS number for check 	r7
;
;		converted IEEE -> TNS number:	r5
;		converted TNS -> IEEE:			r6
;
;**************************************************************

	AREA	assignment_4, CODE

SWI_WriteC	EQU	&0     			;output character in r0 
SWI_Exit	EQU	&11    			;finish program
NULL		EQU	0
	
	ENTRY
;--------------------------------------------------------------------------
Main

	LDR r1, TNSNum
								;r1:= baseNum
	LDR r7, baseNum				;r1:= baseNum

	BL convertIEEEToTNS 
	BL convertTNSToIEEE
	
	CMP r1, r6					;compare stored IEE number to converted number
	MOVNE r0, r6   				;r0:= converted IEEE number
	BLNE Print 

	CMP r5, r7					;compare stored TNS number to converted number
	MOVNE r0, r5				;r0:= converted TNS number
	BLNE Print

	SWI	SWI_Exit				;finished

baseNum
	DCD	&BE800000				;number stored in IEEE floating point

TNSNum
	DCD	&800000FE				;number stored in TNS floating point

signMask 
	DCD &80000000
exponentTNSMask 
	DCD &000001FF
mantissaTNSMask 
	DCD &7FFFFC00

exponentIEEEMask 
	DCD &7F800000
mantissaIEEEMask 
	DCD &007FFFFF

;----------------------------------------------------------------------------

convertTNSToIEEE

	LDR r7, TNSNum
								;r1:= baseNum
	LDR r2, signMask 			;r2:= signMask
	AND r2, r7, r2     			;mask out the sign bit
	
	LDR r3, exponentTNSMask		;r3 := exponentTNSMask
	AND r3, r7, r3				;mask out the exponent
	SUB r3, r3, #256			;remove the TNS bias
	CMP r2, #128 				;check to see if the exponent is too large
	BGT return					;if it is, break
	ADD r3, r3, #127 			;add IEEE bias
	MOV r3, r3, LSL #23 		;place the exponent for IEEE
	
	LDR r4, mantissaTNSMask		;r4:= mantissaTNSMask
	AND r4, r7, r4 				;mask the mantissa
	MOV r4, r4, LSR #9  		;place the mantissa

	ORR r6, r2, r3				;re-pack the number
	ORR r6, r6, r4

return	
	MOV	pc, r14 				;return

;----------------------------------------------------------------------------

convertIEEEToTNS 

	LDR r1, baseNum				;r1:= baseNum
	LDR r2, signMask			;r2:= signMask
	AND r2, r1, r2 				;mask the sign bit

	LDR r3, exponentIEEEMask	;r3:= exponentIEEEMask
	AND r3, r1, r3 				;mask the exponent
	MOV r3, r3, LSR #23 		;place the exponent
	SUB r3, r3, #127 			;remove the IEEE bias
	ADD r3, r3, #256 			;add the TNS bias

	LDR r4, mantissaIEEEMask 	;r4:= mantissaIEEEMask
	AND r4, r1, r4 				;mask the mantissa
	MOV r4, r4, LSR #1 			;remove the least significant bit of the 
								;	mantissa
	MOV r4, r4, LSL #9 			;place the mantissa

	ORR r5, r2, r3				;re-pack the number
	ORR r5, r5, r4 				;

	MOV	pc, r14					;return

;----------------------------------------------------------------------------

Print	
	MOV	r2,#8					;count of nibbles = 8
LOOP	
	MOV	r0,r1,LSR #28			;get top nibble
	CMP 	r0, #9				;hexanumber 0-9 or A-F
	ADDGT 	r0,r0, #"A"-10		;ASCII alphabetic
	ADDLE 	r0,r0, #"0"			;ASCII numeric
	SWI 	SWI_WriteC			; print character
	MOV	r1,r1,LSL #4			;shift left one nibble
	SUBS	r2,r2, #1			;decrement nibble count
	BNE	LOOP					;if more nibbles,loop back
	MOV 	pc, r14				;return


	END
