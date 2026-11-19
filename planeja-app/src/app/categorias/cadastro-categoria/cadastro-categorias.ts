import { Component, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CategoriaService } from '../categoria-service';
import { DadosCategoriasForm } from '../dados-categorias';
import { ValidationErrorResponse } from '../../common/validation/validation-error-model';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { Header } from '../../common/components/header/header';


interface CadastroCategoriasForm {
  nome: FormControl<string>;

}

@Component({
  selector: 'app-cadastro-categorias',
  imports: [  ReactiveFormsModule,
              CommonModule,
              RouterModule,
              Header],
  templateUrl: './cadastro-categorias.html',
  styleUrl: './cadastro-categorias.scss',
})

export class CadastroCategorias implements OnInit {

  form!: FormGroup<CadastroCategoriasForm>;
  service = inject(CategoriaService);
  toast = inject(ToastrService);
  rotaAtiva = inject(ActivatedRoute)

  idCartaoEdicao?: string | null;

  ngOnInit(): void {
    this.form = new FormGroup<CadastroCategoriasForm>({
      nome: new FormControl('', { nonNullable: true, validators: Validators.required })
    
    });
    // this.carregarDadosParaEdicao();
  }


  isFormInvalid(): boolean {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.toast.error('Erro de validação. Verifique os valores informados!');
      return true;
    }
    return false;
  }

  handleSubmit() {
    if (this.isFormInvalid()) {
      return;
    }

    const dadosCategoria = this.form.value as DadosCategoriasForm;
    
    this.service.criar(dadosCategoria)   
      .subscribe({
        next: (response) => {
          this.toast.success('Categoria cadastrada!');
          this.form.reset();
          // this.idCartaoEdicao = null;
        },
        error: (error) => this.onApiError(error)
      });
  }

  private aplicarErrorValidacao(error: ValidationErrorResponse) {
    error.camposInvalidos.forEach(ci => {
      const control = this.form.get(ci.campo);
      if (control) {
        control.setErrors({ apiError: ci.erro });
        control.markAsTouched();
      }
    })
  }

  private onApiError(response: any): void {
    if (response.status === 422) {
      this.aplicarErrorValidacao(response.error);
      this.toast.error('Erro de validação. Verifique os valores informados.');
      return;
    }
    this.toast.error('Ocorreu erro ao processar a requisição.')
    console.error(response.error);
  }
}
