resource "aws_dynamodb_table" "lista_de_mercado" {
  name           = "ListaDeMercado"
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "PK"
  range_key      = "SK"

  attribute {
    name = "PK"
    type = "S"
  }

  attribute {
    name = "SK"
    type = "S"
  }

  tags = {
    Environment = "dev"
  }
}
