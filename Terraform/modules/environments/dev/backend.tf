module "funcao_um" {
  source             = "../../../modules/lambda"
  function_name      = "funcao-um"
  role_name          = "lambda-funcao-um-role"
  handler            = "com.example.FuncaoUmHandler::handleRequest"
  filename           = "C:/Projetos/meu-projeto/Lambda/funcao-um/target/funcao-um-1.0-SNAPSHOT.jar"
  dynamodb_table_arn = "arn:aws:dynamodb:sa-east-1:292210043664:table/ListaDeMercado"
}

module "funcao_dois" {
  source             = "../../../modules/lambda"
  function_name      = "funcao-dois"
  role_name          = "lambda-funcao-dois-role"
  handler            = "com.example.FuncaoDoisHandler::handleRequest"
  filename           = "C:/Projetos/meu-projeto/Lambda/funcao-dois/target/funcao-dois-1.0-SNAPSHOT.jar"
  runtime            = "java11" # opcional, já que é o default no módulo
  dynamodb_table_arn = "arn:aws:dynamodb:sa-east-1:292210043664:table/ListaDeMercado"
}

module "atualizar_item" {
  source             = "../../../modules/lambda"
  function_name      = "atualizar-item"
  role_name          = "lambda-atualizar-item-role"
  handler            = "com.example.AtualizarItemHandler::handleRequest"
  filename           = "C:/Projetos/meu-projeto/Lambda/atualizar-item/target/atualizar-item-1.0-SNAPSHOT.jar"
  runtime            = "java17"
  dynamodb_table_arn = "arn:aws:dynamodb:sa-east-1:292210043664:table/ListaDeMercado"
}



