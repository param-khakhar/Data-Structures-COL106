class GradeInfo implements GradeInfo_{
	private GradeInfo_.LetterGrade l;
	String store;
 	GradeInfo(String s){
 		store = s;
		if(s.equals("A")){
			this.l = LetterGrade.A;
		}
		else if(s.equals("Aminus")) {
			this.l = LetterGrade.Aminus;
		}
		else if(s.equals("B")) {
			this.l = LetterGrade.B;
	}
		else if(s.equals("Bminus")) {
			this.l = LetterGrade.Bminus;
	}
		else if(s.equals("C")) {
			this.l = LetterGrade.C;
	}
		else if(s.equals("Cminus")){
			this.l = LetterGrade.Cminus;
	}
		else if(s.equals("D")) {
			this.l = LetterGrade.D;
	}
		else if(s.equals("E")) {
			this.l = LetterGrade.E;
	}
		else if(s.equals("F")) {
			this.l = LetterGrade.F;
		}
		else {
			this.l = LetterGrade.I;
		}
	}
 	@Override
 	public LetterGrade grade(){
 		return l;
 	}
 	
 	public int value() {
 		return GradeInfo_.gradepoint(l);
 	}
}