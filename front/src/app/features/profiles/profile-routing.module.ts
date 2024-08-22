import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormComponent } from './components/form/form.component';
import { DetailComponent } from './components/detail/detail.component';
import { ListComponent } from './components/list/list.component';


const routes: Routes = [
  { title: 'Profiles', path: '', component: ListComponent },
  { title: 'Profiles - detail', path: 'detail/:id', component: DetailComponent },
  { title: 'Profiles - update', path: 'update/:id', component: FormComponent },
  { title: 'Profiles - create', path: 'create', component: FormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
