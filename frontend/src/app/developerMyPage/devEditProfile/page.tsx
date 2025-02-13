import Link from "next/link";
import { getDevSelf } from "@/app/developerMyPage/page";
import DevEdit from "@/components/DevEdit";

export default async function DevEditProfile() {
  const developer = await getDevSelf();

  return (
    <div className="container">
      <DevEdit dev={developer} />
    </div>
  );
}
