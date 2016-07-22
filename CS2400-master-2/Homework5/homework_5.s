;--------------------------------------------------------------------------
;Homework_5.s
;Michael Menard
;CS2400 Fall 2014
;description:
;			this program generates a random number using an imported file
;			randomnumber.s. Then uses a subroutine to count the number of 
;			ones and the number of zeros. 
;
;			registers:
;						r1 = counter
;						r2 - r4 = subroutine registers
;						r8 = stored random number
;--------------------------------------------------------------------------

	AREA 	homework_5, CODE

	IMPORT randomnumber 

SWI_WriteC	EQU	&0     			;output character in r0 
SWI_Exit	EQU	&11    			;finish program
NULL		EQU	0
		
	ENTRY
Main
	BL randomnumber				;r0 = a1:= 32-bit pseudo-random number
	MOV r8, r0					;r8:= r0 - storing the random number
	BL Print

	BL countOnes				;branch to subroutine countOnes
	BL Print 					;print the counter
	BL countZeros				;r6:= number of zeros in r1
	BL Print 					;print the counter
	SWI	SWI_Exit				;finished
		
;--------------------------------------------------------------------------
	ALIGN
countOnes
	MOV r1, #NULL				;reset counter
	MOV r2, r8					;move random number to be worked with
While	CMP r2, #NULL			;while toop head
	BEQ Done					;break when r2 == NULL
	SUB r3, r2, #1 				;decrement by one
	AND r2, r3, r2 				;decrement the number of 1's
	ADD r1, r1, #1				;increment counter
	B While 					;end of while loop
Done 	
	MOV		pc, r14 			;return

;--------------------------------------------------------------------------
	ALIGN
countZeros
	MOV r1, #NULL				;reset counter
	MOV r2, r8					;move random number to be worked with
	MVN r3, #NULL				;load mask
while	CMP r2, r3 				;while loop head
 	BEQ break 					;break when r2 == &FFFFFFFF
	ADD r4, r2, #1 				;increment by one 
	ORR r2, r4, r2 				;decrement the number of 0's
	ADD r1, r1, #1				; increment counter
	B while 					;end of while loop
break
	MOV		pc, r14 			;return
;--------------------------------------------------------------------------

Print	
	MOV	r2,#8					;count of nibbles = 8
LOOP	MOV	r0,r1,LSR #28		;get top nibble
	CMP 	r0, #9				;hexanumber 0-9 or A-F
	ADDGT 	r0,r0, #"A"-10		;ASCII alphabetic
	ADDLE 	r0,r0, #"0"			;ASCII numeric
	SWI 	SWI_WriteC			; print character
	MOV	r1,r1,LSL #4			;shift left one nibble
	SUBS	r2,r2, #1			;decrement nibble count
	BNE	LOOP					;if more nibbles,loop back
	MOV	r0, #&0a	
	SWI 	SWI_WriteC			; print character
	MOV 	pc, r14				;return

;--------------------------------------------------------------------------
	END
