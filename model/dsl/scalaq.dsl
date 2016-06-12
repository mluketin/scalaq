module persistence
{
  aggregate Questionnaire {
    String  name;
    String  description;
    List<Question> questions;
    specification hasName
      'it => it.name == name'
      {
          String name;
      }

    specification hasNameAndDescription
      'it => it.name == name && it.description == description'
      {
          String name;
          String description;
      }
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
    String other;
  }
  
  value MultiSelectQuestion {
    has mixin QuestionSpec;
    List<String> offeredAnswers;
    String other;
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
  
  value Unanswered {
      has mixin AnswerSpec;
  }
  
  value TextAnswer {
    has mixin AnswerSpec;
    String text;
  }
  
  value OrdinalScaleAnswer {
    has mixin AnswerSpec;
    Int selected;
  }
  
  value SingleSelectAnswer {
    has mixin AnswerSpec;
    Int selected;
    String other;
  }
  
  value MultiSelectAnswer {
    has mixin AnswerSpec;
    List<Int> selectedList;
    String other;
  }
   
  value MatrixAnswer {
     has mixin AnswerSpec;
     List<Int> selectedList;
  } 
}
