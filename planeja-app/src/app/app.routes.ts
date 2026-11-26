import { Routes } from '@angular/router';
import { Template } from './template/template';
import { CadastroCartao } from './cartoes/cadastro-cartao/cadastro-cartao';
import { ListagemCartoes } from './cartoes/listagem-cartoes/listagem-cartoes';
import { ListagemCategorias } from './categorias/listagem-categorias/listagem-categorias';
import { CadastroCategorias } from './categorias/cadastro-categoria/cadastro-categorias'
import { CadastroLancamento } from './lancamentos/cadastro-lancamento/cadastro-lancamento';
import { ListagemLancamentos } from './lancamentos/listagem-lancamentos/listagem-lancamentos';
import { DashboardPage } from './dashboard/dashboard-page/dashboard-page';
import { Login } from './auth/login/login';




export const routes: Routes = [
  {
    path: 'login',
    component: Login
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: 'paginas',
    component: Template,
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        component: DashboardPage
      },
      {
        path: 'cadastro-cartoes',
        component: CadastroCartao
      },
      {
        path: 'listagem-cartoes',
        component: ListagemCartoes
      },
      {
        path: 'cadastro-categorias',
        component: CadastroCategorias
      },
      {
        path: 'listagem-categorias',
        component: ListagemCategorias
      },

      {
        path: 'cadastro-lancamentos',
        component: CadastroLancamento
      },

      {
        path: 'listagem-lancamentos',
        component: ListagemLancamentos
      }

    ]
  }
];
