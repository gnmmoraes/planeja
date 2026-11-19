import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DadosCartaoForm, DetalheCartao } from './dados-cartao';
import { Observable } from 'rxjs';
import { PageResult } from '../common/pagination/page-result'

@Injectable({
  providedIn: 'root',
})
export class CartaoService {

  http = inject(HttpClient);
  baseUrl = 'http://localhost:8080/cartoes';

  criar(dados: DadosCartaoForm) : Observable<DetalheCartao> {    
    return this.http.post<DetalheCartao>(this.baseUrl, dados);
  }

  listar(page: number = 0, size: number = 10) : Observable<PageResult<DetalheCartao>> {
    const url = `${this.baseUrl}?page=${page}&size=${size}`;
    return this.http.get<PageResult<DetalheCartao>>(url);    
  }

  obterPorId(id: string) : Observable<DetalheCartao> {
    return this.http.get<DetalheCartao>(`${this.baseUrl}/${id}`);
  }

  atualizar(id: string, dados: DadosCartaoForm) : Observable<void> {    
    return this.http.put<void>(`${this.baseUrl}/${id}`, dados);
  }

   mudarStatus(id: string) : Observable<void> {    
    return this.http.patch<void>(`${this.baseUrl}/${id}/status`, null);
  }

}
