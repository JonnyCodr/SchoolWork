;**************************************************************
; File name: Homework_3
; Name: Michael Menard
; Date: Sept 26, 2014
; CS2400
; project: Homework 3
; description: this program prints a string,then counts the 
;				number of vowels and re-prints the string with
;				a vowel count.
;**************************************************************

	AREA Blockcopy, CODE

SWI_WriteC	EQU	&0     		;output character in r0 
SWI_Exit	EQU	&11    		;finish program
NULL		EQU	0		;NULL
ZERO		EQU	'0'		;ascii character of 0
	
	ENTRY
Main	
	LDR 	r1, =srcstring		;load the source string into r1
	BL 	 Print			;call print subroutine
	LDR 	r1, =srcstring		;load the source string into r1
Loop1	
	LDRB	r0,[r1], #1		;get a character
	;CMP 	r0, #0			;end mark NUL?
	;BEQ	return
	BL 	checkVowel
	CMP 	r0, #0			;end mark NUL?
	BNE 	Loop1
	LDR 	r1, =srcstring		;load the source string into r1
	BL 	 Print			;call print subroutine
	MOV	r1, r3
	BL 	printCounter
return
	SWI 	SWI_Exit		;finish
;----------------------------------------------------------------------------
	ALIGN
srcstring 
	DCB 	"wOooooooooooooooooooooooo", &0a, &0d, NULL

;----------------------------------------------------------------------------
	ALIGN
Print		
	LDRB	r0,[r1], #1		;get a character
	CMP 	r0, #0			;end mark NUL?
	SWINE 	SWI_WriteC		;if not, print
	BNE	Print
	MOV	pc, r14			;return

;----------------------------------------------------------------------------

printCounter	
	MOV	r2,#8			;count of nibbles = 8
LOOP	MOV	r0,r1,LSR #28		;get top nibble
	CMP 	r0, #9			;hexanumber 0-9 or A-F
	ADDGT 	r0,r0, #"A"-10		;ASCII alphabetic
	ADDLE 	r0,r0, #"0"		;ASCII numeric
	SWI 	SWI_WriteC		; print character
	MOV	r1,r1,LSL #4		;shift left one nibble
	SUBS	r2,r2, #1		;decrement nibble count
	BNE	LOOP			;if more nibbles,loop back
	MOV 	pc, r14			;return
 
;----------------------------------------------------------------------------		

checkVowel	
	TEQ 	r0, #'a'
	TEQNE	r0, #'e'
	TEQNE	r0, #'i'
	TEQNE	r0, #'o'
	TEQNE	r0, #'u'
	SUBEQ	r0,r0, #32
	TEQNE	r0, #'A'
	TEQNE	r0, #'E'
	TEQNE	r0, #'I'
	TEQNE	r0, #'O'
	TEQNE	r0, #'U'
	ADDEQ	r3,r3, #1
	;SWI 	SWI_WriteC		;if not, print
	STRB 	r0, [r1, #-1]		;put the char back 
	MOV	pc, lr 			;return
	
;----------------------------------------------------------------------------

	END

