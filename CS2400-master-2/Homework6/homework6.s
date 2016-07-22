****************************************************************************
* Homework_6.s
*-------------------------------------------------------------------------
* Michael Menard
* CS2400 Fall 2014
* description: This program uses simple encryption techniques to encrypt a 
*		Message using XOR with a secret key and a permutaion. then 
*		reverses the process to decrypt the message. 
*    		  ------------------------------------
*    		  |DCD	print			;op0 |
*   	   	  |DCD	storRegisters		;op1 |
*      		  |DCD	recoverRegisters	;op2 |
*      		  |DCD	encrypt			;op3 |
*      		  |DCD	decrypt			;op4 |
*      		  |DCD 	permutate 		;op5 |
*     		  ------------------------------------
****************************************************************************

	AREA 	homework_6, CODE

SWI_WriteC	EQU	&0     		;output character in r0 
SWI_Exit	EQU	&11    		;finish program
NULL		EQU	0
MAX_LEN 	EQU 	140
		
	ENTRY
Main
	LDR r5, =initMessage
	LDR r6, =encriptedMessage
	LDR r7, =secretKey

	
	LDR r2, =initMessage 		;r2:= address of the first byte of initMessage
	MOV r0, #0			;r0:= 0 for printFunc in jump table
	BL JumpTable			;branch with link to printFunc

	MOV 	r0, #3 			;r0:= 3 for the jump table 
	BL 	JumpTable		;branch with link to encryptFunc

	LDR 	r2, =encriptedMessage 	;r2:= address of the first byte of encryptedMessage
	MOV 	r0, #0 			;r0:= 0 for print function in jumpt table
	BL 	JumpTable		;branch with link to printFunc

	MOV 	r0, #3 			;r0:= 3 for decryptFunct in jump table
	BL 	JumpTable		;branch with link to decryptFunc

	LDR 	r2, =initMessage 	;r2:= address of the first byte of initMessage
	MOV 	r0, #0			;r0:= 0 for printFunc in jump table
	BL 	JumpTable		;branch with link to printFunc
	
	SWI SWI_Exit			;finished
	ALIGN

JumpTable				; label the function
	ADR r3, subTable		; Load address of the jump table
	LDR pc,[r3,r0,LSL #2] 		; Jump to appropriate routine

subTable
	DCD	print			;op0
	DCD	storRegisters		;op1
	DCD	recoverRegisters	;op2
	DCD	encrypt			;op3
	DCD	permutate		;op4
		
print		
	STMFD 	sp!, {R0-R12, lr}	;backup the contents of registers			
	LDRB	r0,[r2], #1		; get a character
	CMP 	r0, #0			; end mark NUL?
	SWINE 	SWI_WriteC		; if not, print
	BNE	print				
	LDMFD	sp!, {r0-r12, lr}	;recover registers			
	MOV	pc,lr			;return

storRegisters
	SUB  	sp, sp, #4		;reserve a word for cpsr
	STMFD 	sp!, {R0-R12, lr}	;backup the contents of registers 
	MRS 	r2, cpsr
	STR	r2, [sp, #14*4]
	LDMFD	sp!, {r0-r12, lr}	;recover registers
	MOV 	pc,lr 			;return
recoverRegisters
	STMFD 	sp!, {R0-R12, lr}	;backup the contents of registers
	LDR	r2, [sp, #14*4]
	MSR	cpsr, r2
	LDMFD	sp!, {r0-r12, lr}
	ADD 	sp, sp, #4
	LDMFD	sp!, {r0-r12, lr}	;recover registers
	MOV	pc,lr 			;return
encrypt
	STMFD 	sp!, {R0-R12, lr}	;backup the contents of registers
	LDRB r8, [r5], #1		;load r8 with the current byte of initMessage
	LDRB r9, [r7]			;load r9 with secretKey byte
	CMP r8, #NULL
	BEQ done1
	EOR r10, r8, r9
	STRB r10, [r6], #1
	BNE	encrypt
done1
	LDMFD	sp!, {r0-r12, lr}	;recover registers
	MOV	pc,lr			;return
decrypt
	STMFD 	sp!, {R0-R12, lr}	;backup the contents of registers
	LDRB 	r8, [r6], #1		;load r8 with the current byte of encryptMessage
	LDRB 	r9, [r7]		;load r9 with secretKey byte
	CMP 	r8, #NULL
	BEQ 	done2
	EOR 	r10, r8, r9
	STRB 	r10, [r5], #1
	BNE 	encrypt
done2
	LDMFD	sp!, {r0-r12, lr}	;recover registers
	MOV	pc,lr			;return
permutate

	;not implemented yet
	
***************************************************************
	AREA Data1, DATA

initMessage 
		DCB "Assignment6 is to use simple cryptography", &0a, &0d, NULL
initEnd		DCB	NULL
	ALIGN

encriptedMessage
		% MAX_LEN + 1 		;reserved zeroed memory for encripted string
encryptEnd 	DCB NULL
	ALIGN

secretKey 	DCB &AA
***************************************************************

	END
