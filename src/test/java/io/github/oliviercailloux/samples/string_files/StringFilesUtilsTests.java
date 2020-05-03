package io.github.oliviercailloux.samples.string_files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

class StringFilesUtilsTests {

	@Test
	void testEquivalentFolders() throws Exception {
		final MyStringFilesUtils utils = MyStringFilesUtils.newInstance();
		utils.setReferenceFolder(Path.of("."));
		assertFalse(utils.setReferenceFolder(Path.of("")));
	}

	@Test
	void testReadLines() throws Exception {
		final MyStringFilesUtils utils = MyStringFilesUtils.newInstance();
		try (FileSystem inMemoryFs = Jimfs.newFileSystem(Configuration.unix())) {
			Files.write(inMemoryFs.getPath("ploum.txt"),
					"Hé !\nBonjour.\n \t\n\r".getBytes(StandardCharsets.ISO_8859_1));

			utils.setReferenceFolder(inMemoryFs.getPath(""));
			final ImmutableList<String> readContent = utils.getContentUsingIso88591Charset("ploum.txt");
			assertEquals(ImmutableList.of("Hé !", "Bonjour.", " \t", ""), readContent);
		}
	}

	/**
	 * <p>
	 * Unfinished test! This only tests the case where the reference folder is
	 * relative, not the case where it is absolute.
	 * </p>
	 * <p>
	 * NOTE: this part is NOT graded, you do not <em>have to</em> finish this test
	 * (but doing so may help with testing your implementation).
	 * </p>
	 */
	@Test
	void testRelativeTo() throws Exception {
		final MyStringFilesUtils utils = MyStringFilesUtils.newInstance();

		utils.setReferenceFolder(Path.of("a", "b"));
		assertEquals("c", utils.getPathRelativeToReference("a/b/c"));

		utils.setReferenceFolder(Path.of(""));
		assertEquals("a\\b\\c", utils.getPathRelativeToReference("a/b/c"));

		utils.setReferenceFolder(Path.of("/root/subfolder"));
		/** TODO… */
	}

}
