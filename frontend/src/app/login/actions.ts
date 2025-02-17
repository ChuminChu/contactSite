"use server";
import { redirect } from "next/navigation";
import { cookies } from "next/headers";
import { signIn } from "@/app/api";

export async function handleSubmit(formData: FormData) {
  const loginType = formData.get("loginType") as string;
  const userId = formData.get("userid") as string;
  const password = formData.get("userpw") as string;

  console.log({ userId, password });

  // `signIn` 함수 호출하여 토큰 받기
  const token = await signIn(userId, password, loginType);

  // userType을 쿠키에 저장
  const userType = loginType === "dev" ? "developer" : "businessmen";

  const cookieStore = await cookies();
  cookieStore.set("token", token, { httpOnly: true });
  cookieStore.set("userType", userType, { httpOnly: true });

  // 로그인 성공 후 리다이렉션
  redirect("/user");
}

export async function getAuthToken() {
  const cookieStore = await cookies();
  return cookieStore.get("token")?.value || "";
}
