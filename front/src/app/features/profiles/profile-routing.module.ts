import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormComponent } from './components/form/form.component';
import { DetailComponent } from './components/detail/detail.component';
import { ListComponent } from './components/list/list.component';
import { AuthGuard } from 'src/app/guards/auth.guard';


const routes: Routes = [
  { 
    title: 'Profiles', 
    path: '', 
    component: ListComponent,
    canActivate: [AuthGuard] 
  },
  { 
    title: 'Profiles - detail', 
    path: 'detail/:id', 
    component: DetailComponent,
    canActivate: [AuthGuard]
  },
  { 
    title: 'Profiles - update', 
    path: 'update/:id', 
    component: FormComponent,
    canActivate: [AuthGuard] 
  },
  { 
    title: 'Profiles - create', 
    path: 'create', 
    component: FormComponent,
    canActivate: [AuthGuard] 
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }

