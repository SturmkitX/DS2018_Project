import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AuthenticationService } from '../../services/authentication.service';
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";

@Component({
    selector: 'app-login',
    templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {
    public loginForm: FormGroup;
    public signUpForm: FormGroup;
    public submitted = false;
    public error = '';

    public formEmail = '';
    public formPassword = '';

    @Output() public loggedIn = new EventEmitter<boolean>();

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService) {}

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['test2@testus.com', Validators.required],
            password: ['testpass', Validators.required]
        });

        this.signUpForm = this.formBuilder.group({
            name: ['', Validators.nullValidator],
            email: ['', Validators.required],
            password: ['', Validators.required]
        });

        // reset login status
        // this.authenticationService.logout();
    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    onSubmit() {
        this.submitted = true;

        this.formEmail = this.f.username.value;
        this.formPassword = this.f.password.value;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.authenticationService.login(this.f.username.value, this.f.password.value)
            .pipe(first())
            .subscribe(
                data => {
                    // this.router.navigate([this.returnUrl], { relativeTo: this.route });
                    console.log(data);
                    if (data) {
                        this.loggedIn.emit(true);
                    }
                },
                error => {
                    this.error = error;
                    alert('Bad username / password combination. Please try again');
                });
    }

    onSignUp() {
        const user = new User();
        user.name = this.signUpForm.controls.name.value;
        user.email = this.signUpForm.controls.email.value;
        user.password = this.signUpForm.controls.password.value;
        user.active = false;
        user.enableEmails = false;
        user.role = 'Regular';

        this.userService.insertUser(user).subscribe(result => {
            if (result) {
                alert('Successfully created an account. Please verify your inbox');
            } else {
                alert('Invalid information. Please check the fields!');
            }
        }, error => {
            alert('E-mail address already in use');
        });
    }
}
