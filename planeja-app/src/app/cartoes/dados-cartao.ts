export class DadosCartaoForm{
  nome!: string;
  bandeira!: string;
}

export class DetalheCartao{
  id!: string
  nome!: string;
  bandeira!: string;
  dataCadastro!: Date;
  ativo!: boolean;
}