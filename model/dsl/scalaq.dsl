module persistence
{
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
}
