import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerPortalCustomerModule),
      },
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.CustomerPortalPersonModule),
      },
      {
        path: 'legal-entity',
        loadChildren: () => import('./legal-entity/legal-entity.module').then(m => m.CustomerPortalLegalEntityModule),
      },
      {
        path: 'customer-email',
        loadChildren: () => import('./customer-email/customer-email.module').then(m => m.CustomerPortalCustomerEmailModule),
      },
      {
        path: 'customer-phone',
        loadChildren: () => import('./customer-phone/customer-phone.module').then(m => m.CustomerPortalCustomerPhoneModule),
      },
      {
        path: 'customer-address',
        loadChildren: () => import('./customer-address/customer-address.module').then(m => m.CustomerPortalCustomerAddressModule),
      },
      {
        path: 'institution',
        loadChildren: () => import('./institution/institution.module').then(m => m.CustomerPortalInstitutionModule),
      },
      {
        path: 'bank-info',
        loadChildren: () => import('./bank-info/bank-info.module').then(m => m.CustomerPortalBankInfoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CustomerPortalEntityModule {}
