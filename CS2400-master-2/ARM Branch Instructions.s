;Branch instructions

; Syntax:
; 	B{L}{condition} expression


; Branch instructions: 
; 	go to the address given by expression, i.e., [R15]:= address 

; 	– L: Branch with link,[R14]<--[R15],
; 		i.e.,[LR]<--[PC]first,then[R15]<--address

; 	– expression:an offset if the address is within 32MB of the current [PC]

; 	– Forexample,
; 			BEQ Next
; 			            ...
; 			      Next  MOV R1, #2
; 			...

; 	– To return, use “MOV PC,LR” if BL was called or 
; 		“LDMFDSP!, {...,PC}” if [LR] has been saved on a full descending stack.


; __________________________________________________________________________________________

; Example: (if-else)
	
	; Java code:

		; ...
		; if(x < y){
		; 	min = x;
		; 	diff = y-x;
		; }
		; else{
		; 	min = y;
		; 	diff = y - x;
		; }
		; ...

	;ARM code

			...
			CMP R0, R1 			;
			BLT if
	else 	MOV R2, R1 			;
			SUB R3, R0, R1 		;
			B Next
	if 		MOV R2, R0 			;
			SUB R3, R1, R0 		;
	Next 	...
;__________________________________________________________________________________________
;Example (while loop)

	; Java code

		; ...
		; i = 0;
		; while (i < 5){
		; 	sum = sum + x;
		; 	i++;
		; }
		; ...

	;ARM code

			...
			MOV R2, #0 			;
	While	CMP R2, #5			;
			BGE DONE 			;
			ADD R1, R1, R0 		;
			ADD R2, R2, #1 		; increments i
			B While 			;
	Done 	...

;__________________________________________________________________________________________

; Branch Instructions (subroutine)

	; C code
		; Branch Instructions (subroutine)

			...
			y = abs(x); ...
			       int abs(int num){
			             int num_abs;
			}
			if (num<0)
			       num_abs = - num;
			else
			       num_abs = num;
			return num_abs;

	;ARM Code (the subroutine may use R0 to receive input and R1 to return output)

					...						; load x to R0	
					BL abs 					; call subroutine abs
					...						; store |x| from R1
			abs 	CMP R0, #0 
					BGE ELSE
					RSB R1, R0, #0
					B DONE 
			ELSE 	MOV R1, R0
			DONE 	MOV PC, LR 				;return




















