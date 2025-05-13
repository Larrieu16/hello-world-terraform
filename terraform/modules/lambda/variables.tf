variable "function_name" {
  description = "Nome da função Lambda"
  type        = string
}

variable "handler" {
  description = "Handler Java da função Lambda"
  type        = string
}

variable "role_name" {
  description = "Nome da role IAM associada à função"
  type        = string
}

variable "filename" {
  description = "Caminho para o .jar da Lambda"
  type        = string
}

variable "runtime" {
  description = "Runtime da função Lambda"
  type        = string
  default     = "java11"
}

variable "dynamodb_table_arn" {
  description = "ARN da tabela DynamoDB utilizada pela função"
  type        = string
}

