//module je kao schema ili package
module persistence
{
  //aggregate je vrsni koncept u dsl gramatici koji je spremiste podataka
  //id je implicitan
  aggregate Questionnaire {
    String  name;
    String  description;
    List<Question> questions;
  }
  
  entity Question {
    String body;
    String description;
    QuestionSpec spec;
  }
  
  mixin QuestionSpec;
  
  value TextInputQuestion{
      has mixin QuestionSpec;
  }
  
  value SingleSelectQuestion{
    has mixin QuestionSpec;
    List<String> offeredAnswers;
  }
  
  value MultiSelectQuestion {
    has mixin QuestionSpec;
    List<String> offeredAnswers;
  }
  
  value OrdinalScaleQuestion {
    has mixin QuestionSpec;
    int min;
    int max;
    String minLabel;
    String maxLabel;
  }
  
  value MatrixQuestion {
    has mixin QuestionSpec;
    List<String> rows;
    List<String> columns;
  }
  
  aggregate CompletedQuestionnaire {
    Questionnaire *questionnaire;
    DateTime completedAt; 
    List<Answer> answers;
  }
  
  entity Answer {
    AnswerSpec spec;
  }
  
  mixin AnswerSpec;
  
  value TextAnswer {
    has mixin AnswerSpec;
    String text;
  }
  
  value SingleAnswer {
    has mixin AnswerSpec;
    Int selected;
  }
  
  value MultipleAnswers {
    has mixin AnswerSpec;
    List<Int> selectedList;
  } 
}
