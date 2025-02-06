'use server';
import {redirect} from "next/navigation";

export async function handleSubmit(formData: FormData) {

    const newFormData = {
        userid: formData.get('userid'),
        userpw: formData.get('userpw'),
    }
    console.log({ ...newFormData });
    redirect('/');
}