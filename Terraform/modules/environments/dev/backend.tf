module "funcao_um" {
  source         = "../../../modules/lambda" #primeiro erro que fiz, coloquei errado o caminho
  function_name  = "funcao-um"
  role_name      = "lambda-funcao-um-role"
  handler        = "com.example.FuncaoUmHandler::handleRequest"
  filename       = "C:/Projetos/meu-projeto/Lambda/funcao-um/target/funcao-um-1.0-SNAPSHOT.jar"

}
module "funcao_dois" {
  source         = "../../../modules/lambda"
  function_name  = "funcao-dois"
  role_name      = "lambda-funcao-dois-role"
  handler        = "com.example.FuncaoDoisHandler::handleRequest"
  filename       = "C:/Projetos/meu-projeto/Lambda/funcao-dois/target/funcao-dois-1.0-SNAPSHOT.jar"
}

