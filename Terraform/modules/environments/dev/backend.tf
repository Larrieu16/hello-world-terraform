module "hello-world" {
  source             = "../../../modules/lambda"
  function_name      = "hello-world"
  role_name          = "lambda-hello-world-role"
  handler            = "com.example.HelloWorldHandler::handleRequest"
  filename           = "C:/Projetos/meu-projeto/Lambda/hello-world/target/hello-world-1.0-SNAPSHOT.jar"
  dynamodb_table_arn = "arn:aws:dynamodb:sa-east-1:292210043664:table/ListaDeMercado"
}

module "adicionar-item" {
  source             = "../../../modules/lambda"
  function_name      = "adicionar-item"
  role_name          = "lambda-adicionar-item-role"
  handler            = "com.example.AdicionarItemHandler::handleRequest"
  filename           = "C:/Projetos/meu-projeto/Lambda/adicionar-item/target/adicionar-item-1.0-SNAPSHOT.jar"
  dynamodb_table_arn = "arn:aws:dynamodb:sa-east-1:292210043664:table/ListaDeMercado"
}

module "atualizar_item" {
  source             = "../../../modules/lambda"
  function_name      = "atualizar-item"
  role_name          = "lambda-atualizar-item-role"
  handler            = "com.example.AtualizarItemHandler::handleRequest"
  filename           = "C:/Projetos/meu-projeto/Lambda/atualizar-item/target/atualizar-item-1.0-SNAPSHOT.jar"
  dynamodb_table_arn = "arn:aws:dynamodb:sa-east-1:292210043664:table/ListaDeMercado"
}

module "deletar_item" {
  source             = "../../../modules/lambda"
  function_name      = "deletar-item"
  role_name          = "lambda-deletar-item-role"
  handler            = "com.example.DeletarItemHandler::handleRequest"
  filename           = "C:/Projetos/meu-projeto/Lambda/deletar-item/target/deletar-item-1.0-SNAPSHOT.jar"
  dynamodb_table_arn = "arn:aws:dynamodb:sa-east-1:292210043664:table/ListaDeMercado"
}




