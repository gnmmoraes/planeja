import { Component, OnInit, inject } from '@angular/core';
import { CategoriaService } from '../categoria-service';
import { PageResult } from '../../common/pagination/page-result';
import { DetalheCategorias } from '../dados-categorias';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import {ToastrService } from 'ngx-toastr';
import { Header } from '../../common/components/header/header';

@Component({
  selector: 'app-listagem-categorias',
  imports: [  CommonModule,
              RouterModule,
              Header],
  templateUrl: './listagem-categorias.html',
  styleUrl: './listagem-categorias.scss',
})

export class ListagemCategorias implements OnInit {

  service = inject(CategoriaService);
  router = inject(Router);
  toast = inject(ToastrService);


  listagem$!: Observable<PageResult<DetalheCategorias>>;
  paginaAtual = 0;
  tamanhoPagina = 10;

  ngOnInit(): void {
    this.listarCategoria();
  }

  listarCategoria() {
    this.listagem$ = this.service.listar(this.paginaAtual, this.tamanhoPagina);
  }

  navegar(pagina: number) {
    this.paginaAtual = pagina;
    this.listarCategoria();
  }

  navegarProximo(listagem: PageResult<DetalheCategorias>) {
    if (!listagem.last) {
      this.navegar(listagem.number + 1);
    }
  }

  navegarAnterior(listagem: PageResult<DetalheCategorias>) {
    if (!listagem.first) {
      this.navegar(listagem.number - 1);
    }
  }

  paginas(totalpages: number): number[] {
    return Array.from({ length: totalpages }, (valor, index) => index);
  }

  registroInicial(listagem: PageResult<DetalheCategorias>) {
    if (listagem.totalElements === 0) {
      return 0;
    }
    return listagem.number * listagem.size + 1;
  }

  registroFinal(listagem: PageResult<DetalheCategorias>) {
    if (listagem.totalElements === 0) {
      return 0;
    }
    return Math.min((listagem.number + 1) * listagem.size, listagem.totalElements);
  }

  mudarStatus(idCategoria: string){
    this.service.mudarStatus(idCategoria)
      .subscribe(next => {
        this.toast.success('Resgistro atualizado com sucesso!');
        this.listarCategoria();
      })
  }
}
