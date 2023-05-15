variable client_id {
  default = ""
}
variable client_secret {
  default = ""
}
variable ssh_public_key {
  default = "~/.ssh/TU_rsa.pub"
}

variable environment {
    default = "dev"
}

variable location {
    default = "westeurope"
}

variable node_count {
  default = 2
}

variable dns_prefix {
  default = "k8stest"
}

variable cluster_name {
  default = "k8stestEAD"
}

variable resource_group {
  default = "bpappacr"
}