import { redirect } from 'next/navigation';

export default function Page() {
  async function createPost(formData: FormData) {
    'use server';

    const rawFormData = {
      title: formData.get('title'),
      content: formData.get('content'),
    };

    console.log({ ...rawFormData });

    // TODO: 게시글 작성 API 호출

    redirect('/posts');
  }

  return (
      <div className="container max-w-2xl mx-auto p-4">
        <h1 className="text-2xl font-bold mb-4">새 게시글 작성</h1>
        <form action={createPost} className="space-y-4">
          <div>
            <label htmlFor="title" className="block mb-2">
              제목
            </label>
            <input
                type="text"
                name="title"
                className="w-full p-2 border rounded"
                required
            />
          </div>
          <div>
            <label htmlFor="content" className="block mb-2">
              내용
            </label>
            <textarea
                name="content"
                className="w-full p-2 border rounded h-40"
                required
            />
          </div>
          <button
              type="submit"
              className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
          >
            작성하기
          </button>
        </form>
      </div>
  );
}