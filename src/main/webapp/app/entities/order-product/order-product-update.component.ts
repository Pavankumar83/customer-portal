import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrderProduct, OrderProduct } from 'app/shared/model/order-product.model';
import { OrderProductService } from './order-product.service';

@Component({
  selector: 'jhi-order-product-update',
  templateUrl: './order-product-update.component.html',
})
export class OrderProductUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    quantity: [],
  });

  constructor(protected orderProductService: OrderProductService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderProduct }) => {
      this.updateForm(orderProduct);
    });
  }

  updateForm(orderProduct: IOrderProduct): void {
    this.editForm.patchValue({
      id: orderProduct.id,
      quantity: orderProduct.quantity,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderProduct = this.createFromForm();
    if (orderProduct.id !== undefined) {
      this.subscribeToSaveResponse(this.orderProductService.update(orderProduct));
    } else {
      this.subscribeToSaveResponse(this.orderProductService.create(orderProduct));
    }
  }

  private createFromForm(): IOrderProduct {
    return {
      ...new OrderProduct(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderProduct>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
