import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DadosCategoriasForm, DetalheCategorias } from './dados-categorias';
import { Observable } from 'rxjs';
import { PageResult } from '../common/pagination/page-result'

@Injectable({
  providedIn: 'root',
})
export class CategoriaService {

  http = inject(HttpClient);
  baseUrl = 'http://localhost:8080/categorias';

  criar(dados: DadosCategoriasForm): Observable<DetalheCategorias> {
    return this.http.post<DetalheCategorias>(this.baseUrl, dados);
  }

  listar(page: number = 0, size: number = 10): Observable<PageResult<DetalheCategorias>> {
    const url = `${this.baseUrl}?page=${page}&size=${size}`;
    return this.http.get<PageResult<DetalheCategorias>>(url);
  }

  mudarStatus(id: string): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/status`, null);
  }

}
