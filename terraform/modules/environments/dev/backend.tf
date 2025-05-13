locals {
  lambda_path_base    = "C:/Projetos/meu-projeto/Lambda"
  dynamodb_table_arn  = "arn:aws:dynamodb:sa-east-1:292210043664:table/ListaDeMercado"
}

module "hello_world" {
  source             = "../../../modules/lambda"
  function_name      = "hello-world"
  role_name          = "lambda-hello-world-role"
  handler            = "com.example.HelloWorldHandler::handleRequest"
  filename           = "${local.lambda_path_base}/hello-world/target/hello-world-1.0-SNAPSHOT.jar"
  dynamodb_table_arn = local.dynamodb_table_arn
}

module "adicionar_item" {
  source             = "../../../modules/lambda"
  function_name      = "adicionar-item"
  role_name          = "lambda-adicionar-item-role"
  handler            = "com.example.AdicionarItemHandler::handleRequest"
  filename           = "${local.lambda_path_base}/adicionar-item/target/adicionar-item-1.0-SNAPSHOT.jar"
  dynamodb_table_arn = local.dynamodb_table_arn
}

module "atualizar_item" {
  source             = "../../../modules/lambda"
  function_name      = "atualizar-item"
  role_name          = "lambda-atualizar-item-role"
  handler            = "com.example.AtualizarItemHandler::handleRequest"
  filename           = "${local.lambda_path_base}/atualizar-item/target/atualizar-item-1.0-SNAPSHOT.jar"
  dynamodb_table_arn = local.dynamodb_table_arn
}

module "deletar_item" {
  source             = "../../../modules/lambda"
  function_name      = "deletar-item"
  role_name          = "lambda-deletar-item-role"
  handler            = "com.example.DeletarItemHandler::handleRequest"
  filename           = "${local.lambda_path_base}/deletar-item/target/deletar-item-1.0-SNAPSHOT.jar"
  dynamodb_table_arn = local.dynamodb_table_arn
}




