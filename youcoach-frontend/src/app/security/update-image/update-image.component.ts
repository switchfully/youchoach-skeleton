import {Component, OnInit} from '@angular/core';
import {CoacheeService} from "../../profile/services/coachee.service";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder} from "@angular/forms";
import {ImageService} from "../services/image.service";

@Component({
  selector: 'app-update-image',
  templateUrl: './update-image.component.html',
  styleUrls: ['./update-image.component.css']
})
export class UpdateImageComponent implements OnInit {
  imageForm = this.formBuilder.group({
    profilePicture: ['']
  });
  tempProfilePicture: File;
  profileId;

  constructor(private coacheeService: CoacheeService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private imageService: ImageService) {
  }

  ngOnInit(): void {
    this.route.parent.params.subscribe(routeParams => this.profileId = routeParams.id);
  }

  onFileChanged(file: File, profilePicture: HTMLImageElement) {
    profilePicture.src = URL.createObjectURL(file);
    this.tempProfilePicture = file;

    if (file.size / (1024 * 1024) > 3) {
      this.imageForm.controls['profilePicture'].setErrors({'tooBig': true});
    } else {
      this.imageForm.controls['profilePicture'].reset();
    }
  }

  onSubmit(image: any) {
    this.coacheeService.uploadImage(this.profileId, this.tempProfilePicture).subscribe(_ => {
      this.imageForm.reset();
      this.tempProfilePicture = null;
    }, error => console.log(error));
  }

  onCancel(profilePicture: HTMLImageElement) {
    this.imageService.downloadImageAsUrl(this.profileId.toString())
      .subscribe(imageUrl => {
        profilePicture.src = imageUrl.changingThisBreaksApplicationSecurity;
        this.imageForm.reset();
        this.tempProfilePicture = null;
      });
  }
}
